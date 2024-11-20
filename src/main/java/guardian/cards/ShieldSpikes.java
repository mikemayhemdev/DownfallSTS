package guardian.cards;


import champ.vfx.SelfSpikesEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import guardian.GuardianMod;
import guardian.stances.DefensiveMode;
import guardian.patches.AbstractCardEnum;
import guardian.powers.LoseThornsPower;

import static guardian.GuardianMod.makeBetaCardPath;

public class ShieldSpikes extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("ShieldSpikes");
    public static final String IMG_PATH = GuardianMod.getResourcePath("cards/sharpenScales.png");
    private static final CardStrings cardStrings;

    public ShieldSpikes() {
        super(ID, cardStrings.NAME, IMG_PATH, 2, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.GUARDIAN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 12;
        this.baseMagicNumber = this.magicNumber = 3;
        this.socketCount = 0;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("ShieldSpikes.png"));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
            upgradeMagicNumber(1);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        addToBot(new VFXAction(new SelfSpikesEffect(Color.CYAN, 5, true, 0.75F)));
        addToBot(new GainBlockAction(p, p, this.block));

        if (p.stance instanceof DefensiveMode) {
            addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber), this.magicNumber));
        }
        brace(8);
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDungeon.player.stance instanceof DefensiveMode ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    public AbstractCard makeCopy() {
        return new ShieldSpikes();
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


