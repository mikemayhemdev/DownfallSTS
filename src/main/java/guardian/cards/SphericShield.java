package guardian.cards;


import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Sozu;
import guardian.GuardianMod;
import guardian.potions.DefensiveModePotion;
import guardian.patches.AbstractCardEnum;

import static guardian.GuardianMod.makeBetaCardPath;
import static hermit.util.Wiz.atb;

public class SphericShield extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("SphericShield");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/sphereShieldSkill.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public SphericShield() {

        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);
        exhaust = true;
        this.isEthereal = true;
        updateDescription();
        loadGemMisc();
        GuardianMod.loadJokeCardImage(this, makeBetaCardPath("SphericShield.png"));
        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        AbstractRelic sozu = AbstractDungeon.player.getRelic(Sozu.ID);
        if (sozu != null) {
            sozu.flash();
        } else {
                AbstractPotion potion = new DefensiveModePotion();
                atb(new ObtainPotionAction(potion));
            }
    }

    public AbstractCard makeCopy() {
        return new SphericShield();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = false;
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
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


