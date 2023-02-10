package hermit.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.util.Wiz;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class Gambit extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(Gambit.class.getSimpleName());
    public static final String IMG = makeCardPath("gambit.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 1;


    // /STAT DECLARATION/


    public Gambit() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 2;
        this.exhaust = true;
        loadJokeCardImage(this, "gambit.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                tmp.group = (ArrayList<AbstractCard>) Wiz.p().discardPile.group.stream()
                        .filter(c -> c.type == CardType.ATTACK)
                        .collect(Collectors.toList());
                for (AbstractCard c : tmp.group) {
                    if (!tmp.isEmpty() && Wiz.hand().size() < BaseMod.MAX_HAND_SIZE) {
                        c.unhover();
                        c.lighten(true);
                        c.setAngle(0.0F);
                        c.drawScale = 0.12F;
                        c.targetDrawScale = 0.75F;
                        c.current_x = CardGroup.DISCARD_PILE_X;
                        c.current_y = CardGroup.DISCARD_PILE_Y;
                        Wiz.p().discardPile.removeCard(c);
                        AbstractDungeon.player.hand.addToTop(c);
                        // Not sure how necessary these two are.
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                        this.addToTop( new ReduceCostForTurnAction(c,1));
                    }
                }
                isDone = true;
            }
        });
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}