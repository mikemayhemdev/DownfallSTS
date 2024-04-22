package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import downfall.downfallMod;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class Sear extends AbstractHexaCard implements HexaPurpleTextInterface {

    public final static String ID = makeID("Sear");

    public Sear() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        baseBurn = burn = 6;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "Sear.png");
        this.keywords.add(downfallMod.keywords_and_proper_names.get("soulburn"));
        this.keywords.add(downfallMod.keywords_and_proper_names.get("afterlife"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new FireballEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.4F));
        burn(m, burn);
        burn(m, burn);
    }

    @Override
    public void afterlife() {
//        atb(new AbstractGameAction() {
//            @Override
//            public void update() {
//                isDone = true;
//                AbstractMonster m = AbstractDungeon.getRandomMonster();
//                if (m == null) return;
//                calculateCardDamage(m);
//                burn(m, burn);
//                att(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m.hb.cX, m.hb.cY), 0.4F));
//            }
//        });
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (m == null) return;
        atb(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m.hb.cX, m.hb.cY), 0.4F));
        burn(m, burn);
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(2);
        }
    }
}