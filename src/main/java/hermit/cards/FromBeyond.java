package hermit.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.actions.FastLoseHPAction;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;
import hermit.util.Wiz;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class FromBeyond extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(FromBeyond.class.getSimpleName());
    public static final String IMG = makeCardPath("from_beyond.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;



    private static final int COST = 1;


    // /STAT DECLARATION/

    public FromBeyond() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        loadJokeCardImage(this, "from_beyond.png");
        magicNumber= baseMagicNumber=5;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int dameg = this.magicNumber;

        for (int i = 0; i<p.exhaustPile.size();i++) {
            Wiz.atb(new AbstractGameAction() {
                @Override
                public void update() {
                    target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                    if (this.target != null && !isDeadOrEscaped(this.target)) {
                        this.addToTop(new FastLoseHPAction(this.target, AbstractDungeon.player, dameg, EnumPatch.HERMIT_GHOSTFIRE));
                    }

                    isDone = true;
                }
            });
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
}