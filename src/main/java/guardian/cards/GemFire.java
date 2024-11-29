package guardian.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.*;
import guardian.GuardianMod;
import guardian.actions.ReduceRightMostStasisAction;
import guardian.orbs.StasisOrb;
import guardian.patches.AbstractCardEnum;
import guardian.powers.LoseThornsPower;
import hermit.actions.ReduceDebuffsAction;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class GemFire extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("GemFire");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG_PATH = "cards/gemFire.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 1;
    private static final int DAMAGE = 12;

    private static final int SOCKETS = 0;

    public GemFire() {
        super(ID, cardStrings.NAME, GuardianMod.getResourcePath(IMG_PATH), COST, cardStrings.DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);
        this.exhaust = true;
        this.baseDamage = DAMAGE;
        this.socketCount = SOCKETS;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, GuardianMod.makeBetaCardPath("GemFire.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));

        // Collect sockets from piles and stasis
        collectSocketsFromGroup(p.hand);
        collectSocketsFromGroup(p.drawPile);
        collectSocketsFromGroup(p.discardPile);
        collectSocketsFromStasis(p);

        // Process sockets (non-SYNTHETIC first, then SYNTHETIC)
        ArrayList<GuardianMod.socketTypes> nonSyntheticSockets = new ArrayList<>();
        ArrayList<GuardianMod.socketTypes> syntheticSockets = new ArrayList<>();
        for (GuardianMod.socketTypes socket : this.sockets) {
            if (socket == GuardianMod.socketTypes.SYNTHETIC) {
                syntheticSockets.add(socket);
            } else {
                nonSyntheticSockets.add(socket);
            }
        }

        nonSyntheticSockets.addAll(syntheticSockets); // Combine lists
        for (GuardianMod.socketTypes socket : nonSyntheticSockets) {
            processSocket(p, m, socket);
        }
    }

    private void collectSocketsFromGroup(CardGroup group) {
        for (AbstractCard c : group.group) {
            if (c instanceof AbstractGuardianCard) {
                AbstractGuardianCard gc = (AbstractGuardianCard) c;
                for (GuardianMod.socketTypes socket : gc.sockets) {
                    if (!this.sockets.contains(socket)) {
                        this.sockets.add(socket);
                    }
                }
            } else if (c.hasTag(GuardianMod.GEM)) {
                if (c instanceof AbstractGuardianCard) {
                    GuardianMod.socketTypes gemType = ((AbstractGuardianCard) c).thisGemsType;
                    if (gemType != null && !this.sockets.contains(gemType)) {
                        this.sockets.add(gemType);
                    }
                }
            }
        }
    }

    private void collectSocketsFromStasis(AbstractPlayer p) {
        for (AbstractOrb orb : p.orbs) {
            if (orb instanceof StasisOrb) {
                AbstractCard stasisCard = ((StasisOrb) orb).stasisCard;
                if (stasisCard instanceof AbstractGuardianCard) {
                    AbstractGuardianCard gc = (AbstractGuardianCard) stasisCard;
                    for (GuardianMod.socketTypes socket : gc.sockets) {
                        if (!this.sockets.contains(socket)) {
                            this.sockets.add(socket);
                        }
                    }
                } else if (stasisCard != null && stasisCard.hasTag(GuardianMod.GEM)) {
                    if (stasisCard instanceof AbstractGuardianCard) {
                        GuardianMod.socketTypes gemType = ((AbstractGuardianCard) stasisCard).thisGemsType;
                        if (gemType != null && !this.sockets.contains(gemType)) {
                            this.sockets.add(gemType);
                        }
                    }
                }
            }
        }
    }


    private void processSocket(AbstractPlayer p, AbstractMonster m, GuardianMod.socketTypes socketType) {
        switch (socketType) {
            case RED:
                applyTemporaryPower(new StrengthPower(p, 2), new LoseStrengthPower(p, 2));
                break;
            case GREEN:
                applyTemporaryPower(new DexterityPower(p, 2), new LoseDexterityPower(p, 2));
                break;
            case LIGHTBLUE:
                applyTemporaryPower(new ThornsPower(p, 4), new LoseThornsPower(p, 4));
                break;
            case ORANGE:
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
                break;
            case WHITE:
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
                break;
            case CYAN:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new CrystalWard(), 1));
                break;
            case BLUE:
                brace(4);
                break;
            case CRIMSON:
                applyVulnerableToAllEnemies(p, 1);
                break;
            case FRAGMENTED:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new CrystalShiv(), 1));
                break;
            case PURPLE:
                weakenAllEnemies(p, 2);
                break;
            case SYNTHETIC:
                AbstractDungeon.actionManager.addToBottom(new ReduceDebuffsAction(p, 1));
                break;
            case YELLOW:
                AbstractDungeon.actionManager.addToBottom(new ReduceRightMostStasisAction());
                break;
        }
    }

    private void applyTemporaryPower(AbstractPower power, AbstractPower lossPower) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, power, power.amount));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, lossPower, lossPower.amount));
    }

    private void applyVulnerableToAllEnemies(AbstractPlayer p, int amount) {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new VulnerablePower(monster, amount, false), amount));
            }
        }
    }

    private void weakenAllEnemies(AbstractPlayer p, int amount) {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new WeakPower(monster, amount, false), amount));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new GemFire();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }
}
