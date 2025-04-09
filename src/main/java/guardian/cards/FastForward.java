package guardian.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.GuardianMod;
import guardian.actions.ReduceRightMostStasisAction;
import guardian.patches.AbstractCardEnum;
import sneckomod.SneckoMod;

import static guardian.GuardianMod.makeBetaCardPath;

public class FastForward extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("FastForward");
    public static final String IMG_PATH = GuardianMod.getResourcePath("cards/accelerate.png");
    private static final CardStrings cardStrings;

    public FastForward() {
        super(ID, cardStrings.NAME, IMG_PATH, 1, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.GUARDIAN, CardRarity.RARE, CardTarget.NONE);
        this.magicNumber = this.baseMagicNumber = 3;
        this.socketCount = 0;
        updateDescription();
        loadGemMisc();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("FastForward.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
        AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.BorderFlashEffect(com.badlogic.gdx.graphics.Color.GOLD, true));
        AbstractDungeon.topLevelEffectsQueue.add(new com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect());

        for (int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new ReduceRightMostStasisAction());
        }
    }

    public AbstractCard makeCopy() {
        return new FastForward();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
//            selfRetain = true;
//            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
//            this.initializeDescription();
            upgradeBaseCost(0);
        }
    }

    public void updateDescription() {

        if (this.socketCount > 0) {
            if (upgraded && cardStrings.UPGRADE_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(cardStrings.UPGRADE_DESCRIPTION, true);
            } else {
                this.rawDescription = this.updateGemDescription(cardStrings.DESCRIPTION, true);
            }
        }
        this.initializeDescription();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


