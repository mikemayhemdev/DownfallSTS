package guardian.cards;



import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import guardian.GuardianMod;

import java.util.ArrayList;

public class PackageDonuDeca extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("PackageDonuDeca");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/packageDonuDeca.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;

    //TUNING CONSTANTS

    private static final int COST = 0;
    private static final int SOCKETS = 0;
    private static final boolean SOCKETSAREAFTER = true;

    //END TUNING CONSTANTS

    public PackageDonuDeca() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);

        this.exhaust = true;
        this.socketCount = SOCKETS;  updateDescription();  loadGemMisc();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);

        ArrayList derp = new ArrayList();
        AbstractCard tmp;

        tmp = new DonusPower();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);
        tmp.modifyCostForCombat(-1);

        tmp = new DecasProtection();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);
        tmp.modifyCostForCombat(-1);

        tmp = new AncientConstruct();
        if (upgraded) tmp.upgrade();
        derp.add(tmp);
        tmp.modifyCostForCombat(-1);

        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect((AbstractCard)derp.get(0), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect((AbstractCard)derp.get(1), (float) Settings.WIDTH * .75F, (float)Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect((AbstractCard)derp.get(2), (float) Settings.WIDTH * .25F, (float)Settings.HEIGHT / 2.0F));

    }

    public AbstractCard makeCopy() {
        return new PackageDonuDeca();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;

            this.initializeDescription();
        }
    }

    public void updateDescription(){

        if (this.socketCount > 0) {
            if (upgraded && UPGRADED_DESCRIPTION != null) {
                this.rawDescription = this.updateGemDescription(UPGRADED_DESCRIPTION,true);
            } else {
                this.rawDescription = this.updateGemDescription(DESCRIPTION,true);
            }
        }
        this.initializeDescription();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}


