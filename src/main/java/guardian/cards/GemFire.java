package guardian.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import guardian.GuardianMod;
import guardian.actions.GemFireAction;
import guardian.orbs.StasisOrb;
import guardian.patches.AbstractCardEnum;
import sneckomod.SneckoMod;

import static guardian.GuardianMod.makeBetaCardPath;


public class GemFire extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("GemFire");
    public static final String NAME;
    public static final String IMG_PATH = "cards/gemFire.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static int gem_count=0;

    //TUNING CONSTANTS
    private static final int UPGRADE_BONUS = 5;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }


    public GemFire() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.exhaust = true;
        this.baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 0;
        this.socketCount = SOCKETS;
        updateDescription();
        loadGemMisc();
        //this.sockets.add(GuardianMod.socketTypes.RED);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("GemFire.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractDungeon.actionManager.addToBottom(new GemFireAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));

    }

    public void applyPowers() {
        this.countCards();
        baseMagicNumber = magicNumber = GemFire.gem_count;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION+cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public void countCards(){
        GemFire.gem_count=0;
        count_gems_from_group(AbstractDungeon.player.hand);
        count_gems_from_group(AbstractDungeon.player.drawPile);
        count_gems_from_group(AbstractDungeon.player.discardPile);
        count_gems_from_group(this.gatherStasisCards());
    }

    public void count_gems_from_group(CardGroup group) {
        for (AbstractCard c : group.group) {
            if (c instanceof AbstractGuardianCard) {
                AbstractGuardianCard gc = (AbstractGuardianCard) c;
                if (gc.socketCount > 0 || c.hasTag(GuardianMod.GEM)) {
                    if (gc.sockets.size() > 0) {
                        for (GuardianMod.socketTypes socket : gc.sockets) {
                            if (socket != null) GemFire.gem_count++;
                        }

                    }
                    if (gc.hasTag(GuardianMod.GEM)) {
                        GemFire.gem_count++;
                    }
                }
            }
        }
    }

    public CardGroup gatherStasisCards(){
        CardGroup stasiscards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof StasisOrb) {
                stasiscards.group.add( ((StasisOrb) o).stasisCard );
            }
        }
        return stasiscards;
    }

    public AbstractCard makeCopy() {
        return new GemFire();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            upgradeBaseCost(1);
        }


    }

    public void updateDescription() {

        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION, true);
            }
        }
        this.initializeDescription();
    }
}


