package slimebound.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;


public class zzzAccelerateToxins extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:zzzAccelerateToxins";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/acceleratetoxins.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public zzzAccelerateToxins() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        //this.magicNumber = this.baseMagicNumber = 6;


        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new BorderFlashEffect(Color.GREEN, true), 0.05F, true));


        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying) && monster.hasPower("Poison"))
                    monster.getPower("Poison").atStartOfTurn();

            }

        }
        if (upgraded) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                flash();
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                    if ((!monster.isDead) && (!monster.isDying) && monster.hasPower("Poison"))
                        monster.getPower("Poison").atStartOfTurn();

                }
            }

        }
    }

    public AbstractCard makeCopy() {

        return new zzzAccelerateToxins();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            //upgradeMagicNumber(2);
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();

        }

    }
}


